import {Button, Image, Layout} from 'antd';
import React, {useState} from 'react'
import "../App.css"
import 'antd/dist/antd.css';

const {Content} = Layout;

function HomePage(props) {

    return (<Content style={{ margin: '24px 16px 0' }}>
                <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                </div>
            </Content>)

}

export default HomePage;