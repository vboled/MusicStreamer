import {Button, Form, Input, Layout, Space} from "antd";
import {Header} from "antd/es/layout/layout";
import React from "react";
import axios from "axios";
import {Redirect, Route, useHistory} from "react-router-dom";
import {SearchOutlined} from "@ant-design/icons";

const onFinish = (values) => {
    console.log("values", values.search)
};

const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
};


function MyHeader() {

    let history = useHistory();

    const routeChange = (values) => {
        let path = `/search/` + values.search;
        history.push(path);
    }

    return <Layout>
        <Header className="site-layout-sub-header-background" style={{ padding: 0 }}>
            <Form
                name="basic"
                labelCol={{
                    span: 80,
                }}
                wrapperCol={{
                    span: 16,
                }}
                initialValues={{
                    remember: true,
                }}
                onFinish={routeChange}
                onFinishFailed={onFinishFailed}
            >
                <Space>
                    <SearchOutlined style={{color:"white"}} />
                    <Form.Item
                        name="search"
                    >
                        <Input />
                    </Form.Item>

                    <Form.Item
                        wrapperCol={{
                            offset: 8,
                            span: 16,
                        }}
                    >
                        <Button type="primary" htmlType="submit">
                            Submit
                        </Button>
                    </Form.Item>
                </Space>
            </Form>
        </Header>
    </Layout>
}

export default MyHeader