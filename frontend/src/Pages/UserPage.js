import {Button, Divider, Layout, List, Space} from 'antd';
import React from 'react'
import "../App.css"
import 'antd/dist/antd.css';
import Avatar from "antd/es/avatar/avatar";
import axios from "axios";


const {Content} = Layout;

function UserPage(props) {

    const logout = () => {
        axios.get("http://localhost:8080/api/v1/auth/logout/",
            {withCredentials:true});
        props.setIsAuth(false)
    }

    return (<Content style={{ margin: '24px 16px 0' }}>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                        <Divider orientation="left"><h1 style={{fontSize:"40px"}}>
                            Hello, {props.userView.user.name}
                        </h1>
                        </Divider>
                            <List
                                size="large"
                                header={<div><h1 style={{fontSize:"30px"}}>
                                    Info:
                                </h1></div>}
                                bordered
                            >
                            <List.Item>
                                <Space>
                                    <h1 style={{fontSize:"30px"}}>
                                        Lastname:
                                    </h1>
                                    <p style={{fontSize:"25px", marginTop:"15px"}}>
                                        {props.userView.user.name}
                                    </p>
                                </Space>
                            </List.Item>

                            <List.Item>
                                <Space>
                                    <h1 style={{fontSize:"30px"}}>
                                        Lastname:
                                    </h1>
                                    <p style={{fontSize:"25px", marginTop:"15px"}}>
                                        {props.userView.user.name}
                                    </p>
                                </Space>
                            </List.Item>
                            </List>
                        <Button type="primary" htmlType="submit" onClick={logout}>
                            Logout
                        </Button>
                    </div>
            </Content>)

}

export default UserPage;