import {Image, Input, Layout, Menu} from 'antd';
import {
    AudioOutlined, HomeOutlined, NotificationOutlined,
    UnorderedListOutlined,
    UploadOutlined,
    UserOutlined,
    VideoCameraOutlined
} from '@ant-design/icons';
import "../App.css"
import 'antd/dist/antd.css';

import Search from "antd/es/input/Search";
import {useState} from "react";
import {random} from "nanoid";
import {Link} from "react-router-dom";

const { Header, Content, Footer, Sider } = Layout;

function UserPage() {

    return <Layout>
                <Header className="site-layout-sub-header-background" style={{ padding: 0 }} />
                <Content style={{ margin: '24px 16px 0' }}>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                        User Page
                    </div>
                </Content>
            </Layout>

}

export default UserPage;