let progress = document.getElementById('barraProgresso');

window.onscroll = function () {
    showProgress();
}

function showProgress() {
    let totalHeigth = document.body.scrollHeight - window.innerHeight;
    let progressHeight = (window.pageYOffset / totalHeigth) * 100;
    progress.style.width = progressHeight + '%';
}