import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { getToken } from '../utils/auth';

function Reports() {
  const [userId, setUserId] = useState('');
  const [contractNotes, setContractNotes] = useState([]);
  const [tradeBook, setTradeBook] = useState([]);
  const [pnl, setPnl] = useState([]);
  const [capitalGains, setCapitalGains] = useState([]);
  const [digilockerUrl, setDigilockerUrl] = useState('');
  const [aadhaarData, setAadhaarData] = useState(null);
  const [panData, setPanData] = useState(null);
  const [accessToken, setAccessToken] = useState('');

  useEffect(() => {
    const token = getToken();
    const email = JSON.parse(atob(token.split('.')[1])).sub;

    axios.get('http://localhost:8080/api/admin/users').then((res) => {
      const user = res.data.find((u) => u.email === email);
      if (user) {
        setUserId(user.id);
        fetchAllReports(user.id);
        fetchDigilockerAuth(user.id);
      }
    });
  }, []);

  const fetchAllReports = async (uid) => {
    try {
      const [notesRes, tradeRes, pnlRes, gainsRes] = await Promise.all([
        axios.get(`http://localhost:8080/api/reports/contract-notes/${uid}`),
        axios.get(`http://localhost:8080/api/reports/trade-book/${uid}`),
        axios.get(`http://localhost:8080/api/reports/pnl/${uid}`),
        axios.get(`http://localhost:8080/api/reports/capital-gains/${uid}`)
      ]);
      setContractNotes(notesRes.data);
      setTradeBook(tradeRes.data);
      setPnl(pnlRes.data);
      setCapitalGains(gainsRes.data);
    } catch (err) {
      console.error('Report fetch failed', err);
    }
  };

  const fetchDigilockerAuth = async (uid) => {
    try {
      const res = await axios.get(`http://localhost:8080/api/kyc/digilocker/auth?state=${uid}`);
      setDigilockerUrl(res.data);
    } catch (err) {
      console.error('Digilocker auth URL failed');
    }
  };

  const handleCallback = async () => {
    const code = prompt('Enter authorization code from DigiLocker URL:');
    try {
      const res = await axios.get(`http://localhost:8080/api/kyc/digilocker/callback?code=${code}&state=${userId}`);
      setAccessToken(res.data.access_token);
      alert('DigiLocker access token retrieved');
    } catch (err) {
      alert('Callback handling failed');
    }
  };

  const fetchAadhaar = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/kyc/digilocker/aadhaar?accessToken=${accessToken}`);
      setAadhaarData(res.data);
    } catch (err) {
      alert('Failed to fetch Aadhaar');
    }
  };

  const fetchPan = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/kyc/digilocker/pan?accessToken=${accessToken}`);
      setPanData(res.data);
    } catch (err) {
      alert('Failed to fetch PAN');
    }
  };

  const renderTable = (title, data, fields) => (
    <div className="mb-5">
      <h5>{title}</h5>
      <table className="table table-bordered">
        <thead>
          <tr>
            {fields.map((field, idx) => <th key={idx}>{field.label}</th>)}
          </tr>
        </thead>
        <tbody>
          {data.map((row, i) => (
            <tr key={i}>
              {fields.map((f, idx) => <td key={idx}>{row[f.key]}</td>)}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );

  return (
    <div className="container mt-4">
      <h3>Reports</h3>

      {renderTable('Contract Notes', contractNotes, [
        { key: 'symbol', label: 'Symbol' },
        { key: 'price', label: 'Price' },
        { key: 'quantity', label: 'Qty' },
        { key: 'type', label: 'Type' },
        { key: 'timestamp', label: 'Date' }
      ])}

      {renderTable('Trade Book', tradeBook, [
        { key: 'symbol', label: 'Symbol' },
        { key: 'price', label: 'Price' },
        { key: 'quantity', label: 'Qty' },
        { key: 'type', label: 'Type' },
        { key: 'timestamp', label: 'Date' }
      ])}

      {renderTable('P&L Report', pnl, [
        { key: 'symbol', label: 'Symbol' },
        { key: 'realizedPnL', label: 'Realized P&L' },
        { key: 'unrealizedPnL', label: 'Unrealized P&L' },
        { key: 'totalPnL', label: 'Total P&L' }
      ])}

      {renderTable('Capital Gains', capitalGains, [
        { key: 'symbol', label: 'Symbol' },
        { key: 'buyPrice', label: 'Buy Price' },
        { key: 'sellPrice', label: 'Sell Price' },
        { key: 'quantity', label: 'Qty' },
        { key: 'gain', label: 'Capital Gain' }
      ])}

      <div className="mt-5">
        <h5>DigiLocker Integration</h5>
        <a className="btn btn-primary me-2" href={digilockerUrl} target="_blank" rel="noreferrer">
          Launch DigiLocker
        </a>
        <button className="btn btn-warning me-2" onClick={handleCallback}>Handle Callback</button>
        <button className="btn btn-outline-secondary me-2" onClick={fetchAadhaar}>Fetch Aadhaar</button>
        <button className="btn btn-outline-secondary" onClick={fetchPan}>Fetch PAN</button>
      </div>

      {aadhaarData && (
        <div className="mt-3">
          <h6>Aadhaar Data:</h6>
          <pre>{JSON.stringify(aadhaarData, null, 2)}</pre>
        </div>
      )}

      {panData && (
        <div className="mt-3">
          <h6>PAN Data:</h6>
          <pre>{JSON.stringify(panData, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

export default Reports;
