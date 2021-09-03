import React, {useState, useEffect} from 'react';
import {useHistory} from "react-router-dom";
import axios from "axios";

const HomeButton = () => {
    let history = useHistory();

    function handleClick() {
        axios.post("http://localhost:8080/api/v1/auth/", {
            login:"owner",
            password:"owner"
        }, {
            withCredentials:true
        }).then(res =>{
            console.log(res.data)
        }
        )
    }

    return (
        <button type="button" onClick={handleClick}>
            Login
        </button>
    );
}

function LoginPage() {

    return (<div>
                <HomeButton/>
            </div>)
}

export default LoginPage