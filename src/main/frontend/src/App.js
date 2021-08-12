import './App.css';
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

const Test = () => {

  const getStatus = () => {
    axios.post("http://localhost:8080/api/v1/auth/", {
      "login":"admin",
      "password":"admin"
    }).
    then(res => {
      console.log(res);
    });
  }

  useEffect(() => {
    getStatus();
  }, []);

  return <h1>Test</h1>
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
    {/*<WhoAmI/>*/}
    <Test/>
    </div>
  );
}

export default App;
