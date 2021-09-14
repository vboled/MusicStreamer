import {Menu} from "antd";
import {Link} from "react-router-dom";
import {HomeOutlined, ProjectTwoTone, UnorderedListOutlined, UserOutlined} from "@ant-design/icons";
import Sider from "antd/es/layout/Sider";
import "../App.css"
import 'antd/dist/antd.css';
import React from 'react'

function AdditionalItems(userView) {
    if (userView.user.role === "ADMIN")
        return <Menu.Item key="5" icon={<ProjectTwoTone />}>
                <Link>
                    Administration
                </Link>
            </Menu.Item>
    else if (userView.user.role === "OWNER")
        return <Menu.Item key="5" icon={<ProjectTwoTone />}>
            <Link to="/owner/">
                Content
            </Link>
        </Menu.Item>
    else return ;
}

function MyMenu(props) {
    return (
        <div>
            <Sider
                style={{
                    overflow: 'auto',
                    height:"100%",
                    left: 0,
                }}
                breakpoint="lg"
                collapsedWidth="0"
                onBreakpoint={broken => {
                    console.log(broken);
                }}
                onCollapse={(collapsed, type) => {
                    console.log(collapsed, type);
                }}
            >
                <Link to={"/"}>
                    <div>
                        <h1 style={{color:"white", marginTop:"16px", marginLeft:"40px"}}>MusicStreamer</h1>
                    </div>
                </Link>
                <Menu theme="dark" mode="inline" defaultSelectedKeys={['5']}>
                    <Menu.Item key="1" icon={<HomeOutlined />}>
                        <Link to={"/"}>
                            Home page
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="2" icon={<UserOutlined />}>
                        <Link to={`/user/${1}`}>
                            View profile
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="3" icon={<UnorderedListOutlined />}>
                        <Link to={"/playlist/"}>
                            Playlists
                        </Link>
                    </Menu.Item>
                    {AdditionalItems(props.userView)}
                </Menu>
            </Sider>
        </div>
    )
}

export default MyMenu;