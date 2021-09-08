import {Layout} from 'antd';
import React, {useEffect, useState} from 'react'
import {Image} from "antd";
import "../App.css"
import 'antd/dist/antd.css';
import LoginPage from "./LoginPage";
import {useHistory} from "react-router-dom";


const { Content} = Layout;

function HomePage(props) {

    return (<Content style={{ margin: '24px 16px 0' }}>
                <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                    <Image src={"/img/userDefault.png"}/>
                </div>
            </Content>)

}

export default HomePage;