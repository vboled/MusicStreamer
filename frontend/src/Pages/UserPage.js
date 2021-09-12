import {Layout} from 'antd';
import React from 'react'
import "../App.css"
import 'antd/dist/antd.css';


const {Content} = Layout;

function UserPage(props) {

    return (<Content style={{ margin: '24px 16px 0' }}>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                        <h1>{props.match.params.id}, {props.userView.user.name}</h1>
                    </div>
            </Content>)

}

export default UserPage;