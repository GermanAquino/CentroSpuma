import axios from "axios";

const API_URL = "http://localhost:8080/productos"; // cambia según tu endpoint real

export const getProductos = async (page = 0, size = 10) => {
  const response = await axios.get(API_URL, {
    params: { page, size },
  });
  return response.data;
};

export const getProducto = (id) => {
  return axios.get(`${API_URL}/${id}`)
    .then(res => res.data);
};