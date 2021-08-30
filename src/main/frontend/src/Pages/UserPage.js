import {Layout} from 'antd';

import "../App.css"
import 'antd/dist/antd.css';
import {useHistory} from "react-router-dom";


const {Content} = Layout;

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

function UserPage(props) {

    return (<Content style={{ margin: '24px 16px 0' }}>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                        <h1>{props.match.params.id}, {props.userView.user.name}</h1>
                    </div>
            </Content>)

}

export default UserPage;