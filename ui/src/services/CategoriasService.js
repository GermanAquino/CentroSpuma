import api from "../api/axios";

const API_URL = "/categorias";

export const getCategorias = async (page = 0, size = 50) => {
  const response = await api.get(`${API_URL}?page=${page}&size=${size}`);
  return response.data.content;
};