import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { getToken } from '../utils/auth';

function Watchlist() {
  const [userId, setUserId] = useState('');
  const [symbol, setSymbol] = useState('');
  const [watchlist, setWatchlist] = useState([]);

  // Fetch logged-in user's ID from token and load watchlist
  useEffect(() => {
    const token = getToken();
    const email = JSON.parse(atob(token.split('.')[1])).sub;

    axios.get('http://localhost:8080/api/admin/users')
      .then((res) => {
        const user = res.data.find((u) => u.email === email);
        if (user) {
          setUserId(user.id);
          fetchWatchlist(user.id);
        }
      })
      .catch((err) => console.error('Failed to fetch user:', err));
  }, []);

  // Get watchlist from backend
  const fetchWatchlist = async (uid) => {
    try {
      const res = await axios.get(`http://localhost:8080/api/watchlist/${uid}`);
      setWatchlist(res.data);
    } catch (err) {
      console.error('Failed to load watchlist', err);
    }
  };

  // Add symbol to watchlist
  const addSymbol = async () => {
    if (!symbol.trim()) return alert('Enter a valid stock symbol');
    try {
      await axios.post('http://localhost:8080/api/watchlist/add', { userId, symbol });
      setSymbol('');
      fetchWatchlist(userId);
    } catch (err) {
      alert('Failed to add symbol');
      console.error(err);
    }
  };

  // Remove symbol from watchlist
  const removeSymbol = async (sym) => {
    try {
      await axios.delete('http://localhost:8080/api/watchlist/remove', {
        data: { userId, symbol: sym }
      });
      fetchWatchlist(userId);
    } catch (err) {
      alert('Failed to remove symbol');
      console.error(err);
    }
  };

  return (
    <div className="container mt-4">
      <h3>Watchlist</h3>

      <div className="mb-3">
        <input
          className="form-control"
          placeholder="Stock Symbol"
          value={symbol}
          onChange={(e) => setSymbol(e.target.value.toUpperCase())}
        />
        <button className="btn btn-success mt-2" onClick={addSymbol}>
          Add to Watchlist
        </button>
      </div>

      <ul className="list-group">
        {watchlist.map((item, i) => (
          <li key={i} className="list-group-item d-flex justify-content-between align-items-center">
            {item.symbol}
            <button className="btn btn-sm btn-danger" onClick={() => removeSymbol(item.symbol)}>
              Remove
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Watchlist;
