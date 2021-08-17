import {Layout, Menu} from 'antd';

import "../App.css"
import 'antd/dist/antd.css';
import {Link} from "react-router-dom";
import {HomeOutlined, NotificationOutlined, UnorderedListOutlined, UserOutlined} from "@ant-design/icons";
import Sider from "antd/es/layout/Sider";


const { Header, Content} = Layout;

function HomePage() {

    return <Layout>
                <Header className="site-layout-sub-header-background" style={{ padding: 0 }} />
                <Content style={{ margin: '24px 16px 0' }}>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                        Home Page
                    </div>
                </Content>
            </Layout>

}

export default HomePage;