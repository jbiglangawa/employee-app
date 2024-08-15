
document.getElementById('login-button').addEventListener('click', evt => {
    evt.preventDefault();
    clearTokenFromStorage();
    sendLoginRequest();
})


window.onload = () => {
    if(window.location.search) {
        const params = new URLSearchParams(window.location.search);
        if(params.get("logout") == "success") {
            logoutSuccessful.showToast();
        }
    }
}