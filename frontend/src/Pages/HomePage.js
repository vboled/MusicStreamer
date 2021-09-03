import {Layout} from 'antd';
import React, {useState} from 'react'

import "../App.css"
import 'antd/dist/antd.css';
import LoginPage from "./LoginPage";


const { Content} = Layout;

function HomePage(props) {

    const [isAnon, setIsAnon] = useState(true)

    function GetFormLogin () {
        if (isAnon)
            return <LoginPage/>
        return <div/>
    }


    return (<Content style={{ margin: '24px 16px 0' }}>
                <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                    <GetFormLogin/>
                </div>
            </Content>)

}

export default HomePage;