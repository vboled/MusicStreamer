import {Layout} from 'antd';
import React, {useEffect, useState} from 'react'

import "../App.css"
import 'antd/dist/antd.css';
import LoginPage from "./LoginPage";
import {useHistory} from "react-router-dom";


const { Content} = Layout;

function HomePage(props) {

    let history = useHistory()

    useEffect(() => {
        if (!props.isAuth) {
            history.push("/login")
        }
    }, [props])

    return (<Content style={{ margin: '24px 16px 0' }}>
                <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                    <h1>Home page</h1>
                </div>
            </Content>)

}

export default HomePage;