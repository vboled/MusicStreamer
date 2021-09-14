import React from "react";
import {Button, Form, Input, Space} from 'antd';
import axios from "axios";

function LoginUserForm(props) {

    function handleClick() {
    }

    const onFinish = (values) => {
        axios.get("http://localhost:8080/api/v1/auth/", {
        params:{
            login:values.username,
            password:values.password
        },
        withCredentials:true
    }).then(res =>{
            console.log(res)
            if (res.status === 200) {
                props.setIsAuth(true)
                props.whoAmI()
            }
        }
    )
    };

    const switchToCreate = () => {
        props.setLogin(false)
    }

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    return (
        <Form
            name="basic"
            labelCol={{
                span: 8,
            }}
            wrapperCol={{
                span: 16,
            }}
            initialValues={{
                remember: true,
            }}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete="off"
        >
            <Form.Item
                label="Username"
                name="username"
                rules={[
                    {
                        required: true,
                        message: 'Please input your username!',
                    },
                ]}
            >
                <Input style={{width:200}}/>
            </Form.Item>

            <Form.Item
                label="Password"
                name="password"
                rules={[
                    {
                        required: true,
                        message: 'Please input your password!',
                    },
                ]}
            >
                <Input.Password style={{width:200}}/>
            </Form.Item>

            <Form.Item
                wrapperCol={{
                    offset: 8,
                    span: 16,
                }}
            >
                <Space size={45}>
                    <Button type="primary" htmlType="submit" onClick={handleClick}>
                        Submit
                    </Button>
                    <Button type="primary" htmlType="submit" onClick={switchToCreate}>
                        Sign up
                    </Button>
                </Space>
            </Form.Item>
        </Form>
    );

}

export default LoginUserForm