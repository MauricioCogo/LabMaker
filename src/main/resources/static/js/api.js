const BASE_URL = "http://localhost:8080";

const api = {

    // ---------- USUÁRIOS ----------
    async login(payload) {
        const r = await fetch(`${BASE_URL}/usuarios/auth`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const data = await r.json();



        if (!data) {
            throw new Error("Credenciais inválidas");
        }
        console.log(data);

        return data;
    },

    async cadastrarUsuario(payload) {
        const r = await fetch(`${BASE_URL}/usuarios`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
        return r.json();
    },

    async listarUsuarios() {
        const r = await fetch(`${BASE_URL}/usuarios`);
        return r.json();
    },

    async deletarUsuario(id) {
        await fetch(`${BASE_URL}/usuarios/${id}`, { method: "DELETE" });
    },


    // ---------- REQUISIÇÕES ----------
    async novaRequisicao(payload) {
        const r = await fetch(`${BASE_URL}/requisicoes`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
        return r.json();
    },

    async listarRequisicoes() {
        const r = await fetch(`${BASE_URL}/requisicoes`);
        return r.json();
    },


    async getFilaUsuario(id) {
        const rs = await fetch(`${BASE_URL}/requisicoes/user/${id}`, { method: "GET" });
        console.log(rs);
        
        return rs.json();
    },

    async deletarRequisicao(id) {
        await fetch(`${BASE_URL}/requisicoes/${id}`, { method: "DELETE" });
    }
};
