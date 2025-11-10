import api from "../api/axios";

const API_URL = "/productos"; 

export const getProductos = async (page = 0, size = 10) => {
  const response = await api.get(API_URL, {
    params: { page, size },
  });
  return response.data;
};

export const getProducto = (id) => {
  return api.get(`${API_URL}/${id}`)
    .then(res => res.data);
};

export const addProducto = async (producto) => {
  const response = await api.post(API_URL, producto);
  return response.data;
};