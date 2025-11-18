import api from "../api/axios";

const API_URL = "/ventas";

export const getVentas = async (page = 0, size = 10) => {
  const response = await api.get(API_URL, {
    params: { page, size },
  });
  return response.data;
};
