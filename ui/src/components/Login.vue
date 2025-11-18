<template>
    <div class="d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 shadow" style="width: 350px;">
            <!--Titulo-->
            <div class="d-flex justify-content-center align-items-center text-black fw-bold fs-4 mb-3">
                <i class="bi bi-slack me-2 fs-3"></i> CentroSpuma
            </div>
            <!--Formulario-->
            <form>
                <!--Usuario-->
                <div class="mb-3">
                    <label for="username" class="form-label">Usuario</label>
                    <input 
                        type="text"
                        id="username"
                        name="username"
                        class="form-control"
                        v-model="loginForm.username"
                        placeholder="Ingrese su usuario"
                    />
                </div>
                <!-- Password-->
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <div class="input-group">
                        <input
                            :type="showPassword ? 'text' : 'password'"
                            id="password"
                            name="password"
                            class="form-control"
                            v-model="loginForm.password"
                            placeholder="Ingrese su contraseña"
                        />
                        <span
                            class="input-group-text"
                            style="cursor: pointer;"
                            @click="showPassword = !showPassword"
                        >
                            <i :class="showPassword ? 'bi bi-eye-slash' : 'bi bi-eye'"></i>
                        </span>
                    </div>
                </div>
                <!--Boton Inicio Sesion-->
                <button type="button" class="btn btn-dark w-100">
                    Iniciar Sesión
                </button>
            </form>

            <!--Errores-->
            <div class="alert alert-danger mt-3 text-center" role="alert" v-if="errors">
                <i class="bi bi-exclamation-triangle-fill"></i> {{ errors }}
            </div>
        </div>
    </div>
</template>

<script setup>
    import { reactive, ref } from 'vue';

    const loginForm = reactive({
        username: '',
        password: '',
    });

    const showPassword = ref(false);
    const errors = ref(null);
</script>