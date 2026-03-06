const BASE_URL = "http://localhost:8081";

// Token Management

function setToken(token) {
    const expires = new Date();
    expires.setHours(expires.getHours() + 24);
    document.cookie = `token=${token}; expires=${expires.toUTCString()}; path=/`;
}

function getToken() {
    const match = document.cookie.match(new RegExp('(^| )token=([^;]+)'));
    return match ? match[2] : null;
}

function clearToken() {
    document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}

// Role Management

function setRole(role) {
    localStorage.setItem("role", role);
}

function getRole() {
    return localStorage.getItem("role");
}

function clearRole() {
    localStorage.removeItem("role");
}

// Alert

function showAlert(message, type = "success") {
    const alertId = "alert-" + Date.now();

    document.getElementById("alertBox").innerHTML = `
        <div id="${alertId}" 
             class="alert alert-${type} alert-dismissible fade show shadow-sm" 
             role="alert">
            ${message}
            <button type="button"
                    class="btn-close"
                    data-bs-dismiss="alert"
                    aria-label="Close"></button>
        </div>
    `;

    setTimeout(() => {
        const alertElement = document.getElementById(alertId);
        if (alertElement) {
            const alert = new bootstrap.Alert(alertElement);
            alert.close();
        }
    }, 6000);
}

// Navigation

function goLogin() {
    window.location.href = "login.html";
}

function goRegister() {
    window.location.href = "register.html";
}

function goProfile() {
    window.location.href = "index.html";
}

function logout() {
    clearToken();
    clearRole();
    goLogin();
}