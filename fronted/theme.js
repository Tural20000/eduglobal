(function() {
    var STORAGE_KEY = 'eduglobal-theme';

    function getTheme() {
        try {
            return localStorage.getItem(STORAGE_KEY) || 'light';
        } catch (e) {
            return 'light';
        }
    }

    function setTheme(theme) {
        try {
            localStorage.setItem(STORAGE_KEY, theme);
        } catch (e) {}
    }

    function applyTheme(theme) {
        var html = document.documentElement;
        var body = document.body;
        html.classList.remove('dark-mode');
        if (body) body.classList.remove('dark-mode', 'theme-ready');
        if (theme === 'dark') {
            html.classList.add('dark-mode');
            if (body) body.classList.add('dark-mode');
        }
        if (body) body.classList.add('theme-ready');
        updateToggleIcon(theme);
    }

    function updateToggleIcon(theme) {
        var btn = document.getElementById('theme-toggle-btn');
        if (!btn) return;
        var icon = btn.querySelector('i');
        if (icon) {
            icon.className = theme === 'dark' ? 'fas fa-sun' : 'fas fa-moon';
        }
    }

    function toggleTheme() {
        var current = getTheme();
        var next = current === 'dark' ? 'light' : 'dark';
        setTheme(next);
        applyTheme(next);
    }

    function createToggleButton() {
        if (document.getElementById('theme-toggle-btn')) return;
        var theme = getTheme();
        var btn = document.createElement('button');
        btn.id = 'theme-toggle-btn';
        btn.type = 'button';
        btn.setAttribute('aria-label', theme === 'dark' ? 'Açıq tema' : 'Qaranlıq tema');
        btn.innerHTML = theme === 'dark' ? '<i class="fas fa-sun"></i>' : '<i class="fas fa-moon"></i>';
        btn.onclick = toggleTheme;
        document.body.appendChild(btn);
    }

    function init() {
        applyTheme(getTheme());
        createToggleButton();
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

    window.EduGlobalTheme = { getTheme: getTheme, setTheme: setTheme, toggleTheme: toggleTheme };
})();
