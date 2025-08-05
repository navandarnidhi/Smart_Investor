import api from '../api'; // ✅ FIXED

export const login = (email, password) =>
  api.post('/auth/login', { email, password });

export const saveToken = (token) => localStorage.setItem('token', token);
export const getToken = () => localStorage.getItem('token');
export const logout = () => localStorage.removeItem('token');
