import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { getToken } from '../utils/auth';

function Funds() {
  const [userId, setUserId] = useState('');
  const [amount, setAmount] = useState('');
  const [balance, setBalance] = useState(0);
  const [ledger, setLedger] = useState([]);

  const fetchUserId = async () => {
    const token = getToken();
    const email = JSON.parse(atob(token.split('.')[1])).sub;
    const userRes = await axios.get(`http://localhost:8080/api/admin/users`);
    const currentUser = userRes.data.find((u) => u.email === email);
    if (currentUser) setUserId(currentUser.id);
  };

  const fetchBalanceAndLedger = async (uid) => {
    const [balanceRes, ledgerRes] = await Promise.all([
      axios.get(`http://localhost:8080/api/funds/balance/${uid}`),
      axios.get(`http://localhost:8080/api/funds/ledger/${uid}`)
    ]);
    setBalance(balanceRes.data);
    setLedger(ledgerRes.data);
  };

  useEffect(() => {
    (async () => {
      await fetchUserId();
    })();
  }, []);

  useEffect(() => {
    if (userId) fetchBalanceAndLedger(userId);
  }, [userId]);

  const handleTransaction = async (type) => {
    if (!amount || isNaN(amount) || amount <= 0) return alert('Enter valid amount');
    try {
      const endpoint = type === 'add' ? 'add' : 'withdraw';
      await axios.post(`http://localhost:8080/api/funds/${endpoint}`, { userId, amount });
      alert(`Funds ${type === 'add' ? 'added' : 'withdrawn'} successfully.`);
      setAmount('');
      fetchBalanceAndLedger(userId);
    } catch (err) {
      alert('Transaction failed');
      console.error(err);
    }
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-4">Funds</h3>

      <div className="mb-3">
        <label>Amount</label>
        <input
          type="number"
          className="form-control"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />
      </div>

      <div className="d-flex gap-2 mb-4">
        <button className="btn btn-success w-100" onClick={() => handleTransaction('add')}>
          Add Funds
        </button>
        <button className="btn btn-danger w-100" onClick={() => handleTransaction('withdraw')}>
          Withdraw Funds
        </button>
      </div>

      <h5>Current Balance: ₹{balance}</h5>

      <h5 className="mt-4">Transaction Ledger</h5>
      <table className="table table-bordered mt-2">
        <thead>
          <tr>
            <th>Type</th>
            <th>Amount</th>
            <th>Date</th>
          </tr>
        </thead>
        <tbody>
          {ledger.map((entry, index) => (
            <tr key={index}>
              <td>{entry.type}</td>
              <td>{entry.amount}</td>
              <td>{new Date(entry.timestamp).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Funds;
