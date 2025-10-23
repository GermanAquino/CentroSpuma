import axios from "axios";

const API_URL = "http://localhost:8080/ventas";

export const getVentas = async (page = 0, size = 10) => {
  const response = await axios.get(API_URL, {
    params: { page, size },
  });
  return response.data;
};
