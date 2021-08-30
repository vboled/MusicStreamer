import {Button, Dropdown, Form, Input, Layout, Menu, Space} from 'antd';

import "../App.css"
import 'antd/dist/antd.css';
import MyHeader from "../Elements/Header";
import {SearchOutlined} from "@ant-design/icons";


const { Header, Content} = Layout;

function HomePage(props) {

    return (<Content style={{ margin: '24px 16px 0' }}>
                <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                </div>
            </Content>)

}

export default HomePage;