import {Layout, Menu} from "antd";
import {Link} from "react-router-dom";
import {HomeOutlined, NotificationOutlined, UnorderedListOutlined, UserOutlined} from "@ant-design/icons";
import {Content, Header} from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import "../App.css"
import 'antd/dist/antd.css';

function MyMenu() {
    return <div>
            <Sider
                style={{
                    overflow: 'auto',
                    minHeight: '100vh',
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
                <div className="logo" >
                    <h1 style={{color:"white"}}>MusicStreamer</h1>
                </div>
                <Menu theme="dark" mode="inline" defaultSelectedKeys={['4']}>
                    <Menu.Item key="1" icon={<HomeOutlined />}>
                        <Link to={"/"}>
                            Home page
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="2" icon={<UserOutlined />}>
                        <Link to={"/user/"}>
                            View profile
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="3" icon={<UnorderedListOutlined />}>
                        <Link to={"/playlist/"}>
                            Playlists
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="4" icon={<NotificationOutlined />}>
                        <Link>
                            Artists
                        </Link>
                    </Menu.Item>
                </Menu>
            </Sider>
    </div>
}

export default MyMenu;