import api from './index'; // stays as is inside api folder

export const register = (userData) => api.post('/auth/register', userData);
export const login = (credentials) => api.post('/auth/login', credentials);
export const verifyOtp = (email, otp) => api.post('/auth/verify-otp', { email, otp });
export const requestLoginOtp = (email) => api.post('/auth/login-otp-request', { email });
export const loginWithOtp = (email, otp) => api.post('/auth/login-otp', { email, otp });
