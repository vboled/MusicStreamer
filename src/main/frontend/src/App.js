import logo from './logo.svg';
import './App.css';
import anxios from 'axios';
import React, {useState, useEffect} from 'react';
import axios from 'axios';

const WhoAmI = () => {

  const [result, setResult] = useState("none");

  const getStatus = () => {
    axios.get("http://localhost:8080/api/v1/whoami").then(res => {
      console.log(res);
      setResult(res.data);
    });
  }

  useEffect(() => {
    getStatus();
  }, []);

  if (result === "anonymous")
    return AnonymousPage()
  else if (result === "none")
    return ErrorPage();
  return AuthorizedPage();
}

function AnonymousPage() {
  return <h1>Anonymous Page</h1>
}

function AuthorizedPage() {
  return <h1>Authorized Page</h1>
}

function ErrorPage() {
  return <h1>Error Page</h1>
}

function App() {
  return (
    <div className="App">
    <WhoAmI/>
    </div>
  );
}

export default App;
