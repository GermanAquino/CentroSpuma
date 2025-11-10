import axios from "axios";
import router from "../router";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 500) {
      router.push("/500");
    }
    return Promise.reject(error);
  }
);

export default api;