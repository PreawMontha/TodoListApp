import axios from 'axios';

export const todoListAPI = process.env.REACT_APP_TODO_LIST_API_URL;

export const todoListInstance = axios.create({
  baseURL: todoListAPI,
  timeout: 30000,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
    Accept: 'application/json'
  }
})