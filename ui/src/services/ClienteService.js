import axios from 'axios';

const API_URL = 'http://localhost:8080/clientes';

export const getClientes = async (page = 0, size = 10, nombre = '') => {
  const response = await axios.get(API_URL, {
    params: { page, size, nombre }
  });
  return response.data;
};

export const getCliente = (id) => {
  return axios.get(`${API_URL}/${id}`)
    .then(res => res.data);
};