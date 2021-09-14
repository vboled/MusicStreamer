import React, {useState, useEffect} from 'react'
import { Select } from 'antd';
import {message, Button, Form, Input, Space} from 'antd';
import axios from "axios";
import SelectRegion from "./SelectRegion"

const { Option } = Select;

function CreateUserForm(props) {

    const inputStyle = {width:200}

    const [code, setCode] = useState("+1")
    const [regionID, setRegionID] = useState(0)

    function handleClick() {

    }

    const onFinish = (values) => {
        console.log(values, code)
        axios.post("http://localhost:8080/api/v1/create/", {
            "name":values.name,
            "lastName":values.lastName,
            "userName":values.username,
            "email":values.email,
            "password":values.password,
            "phoneNumber":code + values.phone,
            "regionID":regionID
        }).then(res =>{
                console.log(res)
                if (res.status === 200) {
                    props.setIsAuth(true)
                    props.setLogin(true)
                }
                console.log(res.data)
            }
        ).catch(err=>{
            message.error(err.response.data)
        })
    };

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    const [regions, setRegions] = useState([])

    const getRegions = () => {
        axios.get("http://localhost:8080/api/v1/region/all/").then(
            res => {
                console.log("regions:", res.data)
                setRegions(res.data)
                console.log(regions)
            }
        )
    }

    useEffect(() => {
        getRegions();
    }, [])

    function handleChange(value) {
        setCode(regions[value].code)
        setRegionID(regions[value].id)
        console.log(`selected ${value}`);
    }

    const children = [];
    for (let i = 0; i < regions.length; i++) {
        children.push(<Option key={i}>{regions[i].name + " " + regions[i].code}</Option>);
    }

    const switchToLogin = () => {
        props.setLogin(true)
    }

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
            style={{display:"flex", flexDirection:"column"}}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete="off"
        >

            <Form.Item
                label="Name"
                name="name"
                rules={[
                    {
                        required: true,
                        message: 'Please input name!',
                    },
                ]}
            >
                <Input style={inputStyle}/>
            </Form.Item>

            <Form.Item
                label="LastName"
                name="lastName"
                rules={[
                    {
                        required: true,
                        message: 'Please input lastName!',
                    },
                ]}
            >
                <Input style={inputStyle}/>
            </Form.Item>
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
                <Input style={inputStyle}/>
            </Form.Item>

            <Form.Item
                label="Password"
                name="password"
                rules={[
                    {
                        required: true,
                        message: 'Please input password!',
                    },
                ]}
            >
                <Input.Password style={inputStyle}/>
            </Form.Item>

            <Form.Item
                label="Email"
                name="email"
                rules={[
                    {
                        required: true,
                        message: 'Please input email!',
                    },
                ]}
            >
                <Input style={inputStyle}/>
            </Form.Item>

            <Form.Item
                label="Code"
                name="code"
                rules={[
                    {
                        required: true,
                        message: 'Please choose region!',
                    },
                ]}
            >
                <Select defaultValue="Choose Region" style={{ width: 200 }} onChange={handleChange}>
                    {children}
                </Select>
            </Form.Item>

            <Form.Item
                label="Phone"
                name="phone"
                rules={[
                    {
                        required: true,
                        message: 'Please input phone!',
                    },
                ]}
            >
                <Input style={inputStyle}/>
            </Form.Item>

            <Form.Item
                wrapperCol={{
                    offset: 8,
                    span: 16,
                }}
            >
                <Space size={51}>
                    <Button type="primary" htmlType="submit" onClick={handleClick}>
                        Submit
                    </Button>
                    <Button type="primary" htmlType="submit" onClick={switchToLogin}>
                        Sign in
                    </Button>
                </Space>
            </Form.Item>
        </Form>
    );

}

export default CreateUserForm