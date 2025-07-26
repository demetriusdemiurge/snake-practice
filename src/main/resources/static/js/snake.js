const API_BASE_URL = '/api/records';

const canvas = document.getElementById('game');
const ctx = canvas.getContext('2d');
const box = 20;
const canvasSize = 400;

let snake = [{ x: 9 * box, y: 10 * box }];
let direction = null;
let food = randomPosition();
let score = 0;
let gameInterval;
let playerName = '';
let snakeColor = '#00ff00';
let fieldColor = '#111111';
let gameStarted = false;
let difficulty = 'medium';
let volume = 0.7;
let currentTrack = 1;

let speedMap = { easy: 300, medium: 200, hard: 50 };

const foodImg = new Image();
foodImg.src = '/images/sig.png';

const musicTracks = {
    0: null,
    1: '/bgm/loading_theme.mp3',
    2: '/bgm/battle5.mp3',
    3: '/bgm/stealth1.mp3',
    4: '/bgm/belaya_noch_ch.mp3',
    5: '/bgm/kukushka.mp3',
    6: '/bgm/pachka_sigaret.mp3',
    7: '/bgm/dorm.mp3'
};
const bgMusic = new Audio();
bgMusic.volume = volume;

init();

function init() {
    showGreeting();
    draw();
    loadLeaderboard();
    setupEventListeners();
}

async function showGreeting() {
    playerName = prompt('Введите имя:', 'Игрок') || 'Игрок';
    document.getElementById('player').textContent = 'Игрок: ' + playerName;
    await loadPlayerSettings(playerName);
}

async function loadPlayerSettings(nickname) {
    try {
        const res = await fetch(`${API_BASE_URL}/${nickname}`);
        if (!res.ok) return;

        const data = await res.json();

        document.getElementById('snakeColor').value = data.color;
        document.getElementById('fieldColor').value = data.backgroundColor;
        document.getElementById('difficulty').value = data.difficulty;
        document.getElementById('volumeControl').value = data.volume;

        snakeColor = data.color;
        fieldColor = data.backgroundColor;
        difficulty = data.difficulty;
        volume = data.volume;
        bgMusic.volume = volume;
    } catch (err) {
        console.warn('Ошибка загрузки настроек:', err);
    }
}

async function saveRecord() {
    const recordData = {
        nickname: playerName,
        record: score,
        volume: volume,
        color: snakeColor,
        backgroundColor: fieldColor,
        difficulty: difficulty
    };
    try {
        const res = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(recordData)
        });
        if (!res.ok) throw new Error('Ошибка сохранения');
        alert('Рекорд сохранён!');
        loadLeaderboard();
    } catch (err) {
        alert('Ошибка: ' + err.message);
    }
}

async function loadLeaderboard() {
    try {
        const res = await fetch(`${API_BASE_URL}/top/10`);
        const data = await res.json();
        displayLeaderboard(data);
    } catch {
        document.getElementById('records-list').innerHTML = 'Ошибка загрузки';
    }
}

function displayLeaderboard(records) {
    const list = document.getElementById('records-list');
    if (!records.length) {
        list.innerHTML = 'Пока нет рекордов';
        return;
    }
    list.innerHTML = records.map((r, i) =>
        `<div>${i + 1}. ${r.nickname}: ${r.record} (${r.difficulty})</div>`
    ).join('');
}

function setupEventListeners() {
    document.addEventListener('keydown', e => {
        if (e.key === 'ArrowLeft' && direction !== 'RIGHT') direction = 'LEFT';
        if (e.key === 'ArrowRight' && direction !== 'LEFT') direction = 'RIGHT';
        if (e.key === 'ArrowUp' && direction !== 'DOWN') direction = 'UP';
        if (e.key === 'ArrowDown' && direction !== 'UP') direction = 'DOWN';
        if (e.key === ' ' && !gameInterval) startGame();
    });

    document.getElementById('startBtn').addEventListener('click', () => {
        snakeColor = document.getElementById('snakeColor').value;
        fieldColor = document.getElementById('fieldColor').value;
        difficulty = document.getElementById('difficulty').value;
        startGame();
    });

    document.getElementById('saveBtn').addEventListener('click', saveRecord);
    document.getElementById('volumeControl').addEventListener('input', e => {
        volume = parseFloat(e.target.value);
        bgMusic.volume = volume;
        updateMusicButtonIcon();
    });

    document.getElementById('musicSelect').addEventListener('change', e => {
        currentTrack = parseInt(e.target.value);
        playMusic(currentTrack);
    });

    document.getElementById('toggleMusic').addEventListener('click', toggleMusic);
}

function draw() {
    ctx.fillStyle = fieldColor;
    ctx.fillRect(0, 0, canvasSize, canvasSize);

    for (let i = 0; i < snake.length; i++) {
        ctx.fillStyle = i === 0 ? snakeColor : shadeColor(snakeColor, -30);
        ctx.fillRect(snake[i].x, snake[i].y, box, box);
        ctx.strokeStyle = '#222';
        ctx.strokeRect(snake[i].x, snake[i].y, box, box);
    }

    if (foodImg.complete) {
        ctx.drawImage(foodImg, food.x, food.y, box, box);
    }

    document.getElementById('score').textContent = 'Счёт: ' + score;
}

function update() {
    let head = { ...snake[0] };
    if (direction === 'LEFT') head.x -= box;
    if (direction === 'RIGHT') head.x += box;
    if (direction === 'UP') head.y -= box;
    if (direction === 'DOWN') head.y += box;

    if (head.x < 0) head.x = canvasSize - box;
    if (head.x >= canvasSize) head.x = 0;
    if (head.y < 0) head.y = canvasSize - box;
    if (head.y >= canvasSize) head.y = 0;

    if (collision(head, snake)) {
        endGame();
        return;
    }

    if (head.x === food.x && head.y === food.y) {
        score++;
        food = randomPosition();
    } else {
        snake.pop();
    }

    snake.unshift(head);
    draw();
}

function startGame() {
    if (gameInterval) return;
    resetGame();
    direction = 'RIGHT';
    let speed = speedMap[difficulty];
    gameInterval = setInterval(update, speed);
    gameStarted = true;
    document.getElementById('saveBtn').disabled = true;
    playMusic(currentTrack);
}

function endGame() {
    clearInterval(gameInterval);
    gameInterval = null;
    alert('Игра окончена! Счёт: ' + score);
    document.getElementById('saveBtn').disabled = false;
}

function resetGame() {
    snake = [{ x: 9 * box, y: 10 * box }];
    direction = null;
    food = randomPosition();
    score = 0;
    draw();
}

function collision(head, body) {
    return body.slice(1).some(segment => segment.x === head.x && segment.y === head.y);
}

function randomPosition() {
    const cells = [];
    for (let x = 0; x < canvasSize; x += box) {
        for (let y = 0; y < canvasSize; y += box) {
            if (!snake.some(s => s.x === x && s.y === y)) {
                cells.push({ x, y });
            }
        }
    }
    return cells[Math.floor(Math.random() * cells.length)];
}

function playMusic(track) {
    if (track === 0) {
        bgMusic.pause();
        return;
    }
    bgMusic.src = musicTracks[track];
    bgMusic.loop = true;
    bgMusic.play().catch(e => console.log('Нужен клик для воспроизведения'));
}

function toggleMusic() {
    if (bgMusic.paused) {
        bgMusic.volume = volume;
        bgMusic.play().catch(e => {});
    } else {
        bgMusic.pause();
    }
    updateMusicButtonIcon();
}

function updateMusicButtonIcon() {
    const icon = (bgMusic.paused || volume === 0) ? '🔇' : '🔊';
    document.getElementById('toggleMusic').textContent = icon;
}

function shadeColor(color, percent) {
    let R = parseInt(color.substring(1, 3), 16);
    let G = parseInt(color.substring(3, 5), 16);
    let B = parseInt(color.substring(5, 7), 16);
    R = parseInt(R * (100 + percent) / 100);
    G = parseInt(G * (100 + percent) / 100);
    B = parseInt(B * (100 + percent) / 100);
    R = R < 255 ? R : 255;
    G = G < 255 ? G : 255;
    B = B < 255 ? B : 255;
    return `#${R.toString(16).padStart(2, '0')}${G.toString(16).padStart(2, '0')}${B.toString(16).padStart(2, '0')}`;
}
