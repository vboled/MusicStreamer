import React from "react";
import { Form, Input, Button, Checkbox } from 'antd';
import {useHistory} from "react-router-dom";

function LoginUserForm() {

    let history = useHistory();

    function handleClick() {
        // axios.post("http://localhost:8080/api/v1/auth/", {
        //     login:"owner",
        //     password:"owner"
        // }, {
        //     withCredentials:true
        // }).then(res =>{
        //     console.log(res.data)
        // }
        // )
        history.push("/")
    }

    const onFinish = (values) => {
        console.log('Success:', values);
    };

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
                <Input />
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
                <Input.Password />
            </Form.Item>

            <Form.Item
                label="Email"
                name="email"
                rules={[
                    {
                        required: true,
                        message: 'Please input your email!',
                    },
                ]}
            >
                <Input.Password />
            </Form.Item>

            <Form.Item
                wrapperCol={{
                    offset: 8,
                    span: 16,
                }}
            >
                <Button type="primary" htmlType="submit" onClick={handleClick}>
                    Submit
                </Button>
            </Form.Item>
        </Form>
    );

}

export default LoginUserForm