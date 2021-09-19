import React, {useState} from 'react';
import {Content} from "antd/es/layout/layout";
import CreateUserForm from "../Elements/CreateUserForm";
import LoginUserForm from "../Elements/LoginUserForm";

function LoginPage(props) {

    const[login, setLogin] = useState(true)

    function GetForm(props) {
        if (!props.login)
            return <CreateUserForm
                setLogin={setLogin}/>
        return <LoginUserForm
            setIsAuth={props.setIsAuth}
            whoAmI={props.whoAmI}
            setLogin={setLogin}
        />
    }

    return (<Content style={{ margin: '24px 16px 0' }}>
        <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                <GetForm
                    login={login}
                    setIsAuth={props.setIsAuth}
                    whoAmI={props.whoAmI}
                />
        </div>
    </Content>)
}

export default LoginPage