import logo from './logo.svg';
import './App.css';
import anxios from 'axios';
import React, {useState, useEffect} from 'react';
import axios from 'axios';

const UserProfiles = () => {

  const [UserProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/admin/all/").then(res => {
      console.log(res);
      setUserProfiles(res.data);
    });
  }

  useEffect(() => {
     fetchUserProfiles();
  }, []);


  return UserProfiles.map((userProfile, index) => {

    return (
      <div key={index}>
        <h1>{userProfile.userName}</h1>
        <p>{userProfile.email}, {userProfile.phoneNumber}, {userProfile.name}</p>
      </div>
    )
  })
}

function App() {
  return (
    <div className="App">
    <UserProfiles/>
    </div>
  );
}

export default App;
