(function(){
    try {
        if (localStorage.getItem('eduglobal-theme') === 'dark') {
            document.documentElement.classList.add('dark-mode');
        }
    } catch (e) {}
})();
