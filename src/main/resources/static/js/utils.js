const utils = {

    getCurrentUser() {
        return JSON.parse(localStorage.getItem("usuario"));
    },

    saveUser(user) {
        localStorage.setItem("usuario", JSON.stringify(user));
    },

    logout() {
        localStorage.removeItem("usuario");
    }
};
