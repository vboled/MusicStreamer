import {Image, Input, Layout, Menu} from 'antd';

import "../App.css"
import 'antd/dist/antd.css';
import {useHistory} from "react-router-dom";
import MyHeader from "../Elements/Header";


const { Header, Content, Footer, Sider } = Layout;

const HomeButton = () => {
    let history = useHistory();

    function handleClick() {
        history.push("/");
    }

    return (
        <button type="button" onClick={handleClick}>
            Go home
        </button>
    );
}

function UserPage() {

    return <Layout>
                 {MyHeader()}
                <Content style={{ margin: '24px 16px 0' }}>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                        {HomeButton()}
                    </div>
                </Content>
            </Layout>

}

export default UserPage;