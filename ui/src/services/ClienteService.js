import api from "../api/axios";

const API_URL = '/clientes';

export const getClientes = async (page = 0, size = 10, nombre = '') => {
  const response = await api.get(API_URL, {
    params: { page, size, nombre }
  });
  return response.data;
};

export const getCliente = (id) => {
  return api.get(`${API_URL}/${id}`)
    .then(res => res.data);
};