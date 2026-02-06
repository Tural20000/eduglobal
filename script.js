const questions = [
    { q: "I ___ a student.", a: ["am", "is", "are"], c: "am" }, // A1
    { q: "She ___ to school every day.", a: ["go", "goes", "going"], c: "goes" }, // A1
    { q: "Where ___ you yesterday?", a: ["was", "were", "did"], c: "were" }, // A2
    { q: "I have ___ to London twice.", a: ["be", "been", "was"], c: "been" }, // B1
    { q: "If I ___ you, I would study more.", a: ["was", "am", "were"], c: "were" } // B2
];

let currentQ = 0;
let score = 0;

function showQuestion() {
    const qData = questions[currentQ];
    document.getElementById("question-text").innerText = `${currentQ + 1}. ${qData.q}`;
    const optionsBox = document.getElementById("options-box");
    optionsBox.innerHTML = "";
    
    qData.a.forEach(opt => {
        const btn = document.createElement("button");
        btn.innerText = opt;
        btn.onclick = () => nextQuestion(opt);
        optionsBox.appendChild(btn);
    });
}

function nextQuestion(answer) {
    if(answer === questions[currentQ].c) score++;
    currentQ++;
    
    if(currentQ < questions.length) {
        showQuestion();
    } else {
        showResult();
    }
}

function showResult() {
    document.getElementById("quiz-box").style.display = "none";
    document.getElementById("result-screen").style.display = "block";
    
    let level = "";
    let desc = "";
    
    if(score <= 1) { level = "A1 - Beginner"; desc = "Siz yenicə başlayırsınız!"; }
    else if(score == 2) { level = "A2 - Elementary"; desc = "Gündəlik mövzuları başa düşürsünüz."; }
    else if(score == 3) { level = "B1 - Intermediate"; desc = "Sərbəst danışmağa yaxınsınız."; }
    else { level = "B2+ - Advanced"; desc = "Artıq peşəkar sayılırsınız!"; }
    
    document.getElementById("user-level").innerText = level;
    document.getElementById("level-desc").innerText = desc;
}

showQuestion();const questions = [
    { q: "I ___ a student.", a: ["am", "is", "are"], c: "am" }, // A1
    { q: "She ___ to school every day.", a: ["go", "goes", "going"], c: "goes" }, // A1
    { q: "Where ___ you yesterday?", a: ["was", "were", "did"], c: "were" }, // A2
    { q: "I have ___ to London twice.", a: ["be", "been", "was"], c: "been" }, // B1
    { q: "If I ___ you, I would study more.", a: ["was", "am", "were"], c: "were" } // B2
];

let currentQ = 0;
let score = 0;

function showQuestion() {
    const qData = questions[currentQ];
    document.getElementById("question-text").innerText = `${currentQ + 1}. ${qData.q}`;
    const optionsBox = document.getElementById("options-box");
    optionsBox.innerHTML = "";
    
    qData.a.forEach(opt => {
        const btn = document.createElement("button");
        btn.innerText = opt;
        btn.onclick = () => nextQuestion(opt);
        optionsBox.appendChild(btn);
    });
}

function nextQuestion(answer) {
    if(answer === questions[currentQ].c) score++;
    currentQ++;
    
    if(currentQ < questions.length) {
        showQuestion();
    } else {
        showResult();
    }
}

function showResult() {
    document.getElementById("quiz-box").style.display = "none";
    document.getElementById("result-screen").style.display = "block";
    
    let level = "";
    let desc = "";
    
    if(score <= 1) { level = "A1 - Beginner"; desc = "Siz yenicə başlayırsınız!"; }
    else if(score == 2) { level = "A2 - Elementary"; desc = "Gündəlik mövzuları başa düşürsünüz."; }
    else if(score == 3) { level = "B1 - Intermediate"; desc = "Sərbəst danışmağa yaxınsınız."; }
    else { level = "B2+ - Advanced"; desc = "Artıq peşəkar sayılırsınız!"; }
    
    document.getElementById("user-level").innerText = level;
    document.getElementById("level-desc").innerText = desc;
}

showQuestion();
