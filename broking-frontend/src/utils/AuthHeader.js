import { getToken } from './auth';

export const authHeader = () => {
  const token = getToken(); // read token from localStorage
  if (token) {
    return {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };
  } else {
    return {};
  }
};
