import './App.css';
import React, {useState, useEffect} from 'react';
import axios from 'axios';
import Cookie from 'js-cookie'
import Anonymous from "./Anonymous";
import SignedUser from "./SignedUser";

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
    return Anonymous();
  else if (result === "none")
    return ErrorPage();
  return SignedUser();
}

const Test = () => {

  const getStatus = () => {
    axios.post("http://localhost:8080/api/v1/auth/", {
      "login":"admin",
      "password":"admin"
    }).
    then(res => {
      Cookie.set("User", res.data)
    });
  }

  const test = () => {
    axios.get("http://localhost:8080/api/v1/user/info/", {withCredentials:true}).
    then(res => {
      console.log(res.data)
    });
  }

  useEffect(() => {
    test();
  }, []);

  return <h1>Test</h1>
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
