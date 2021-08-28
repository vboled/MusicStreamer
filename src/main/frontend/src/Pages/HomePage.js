import {Dropdown, Layout, Menu} from 'antd';

import "../App.css"
import 'antd/dist/antd.css';
import MyHeader from "../Elements/Header";


const { Header, Content} = Layout;

function HomePage() {

    return <Layout>
            {MyHeader()}
            <Content style={{ margin: '24px 16px 0' }}>
                <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                </div>
            </Content>
            </Layout>

}

export default HomePage;