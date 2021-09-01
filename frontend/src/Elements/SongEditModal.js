import Modal from "antd/es/modal/Modal";
import {Button, Form, Input, Space} from "antd";
import {CloseOutlined} from "@ant-design/icons";
import axios from "axios";
import {useState} from "react";
import React from 'react'

function SongEditModal(props) {

    const deleteSong = () => {
        axios.delete("http://localhost:8080/api/v1/songs/", {
            params:{
                id:props.editedSong.id
            }
        }).then(r => {
            props.setIsEditModalVisible(false);
            props.updatePage()
        })
    }

    const [state, setState] = useState()

    const fileSelectedHandler = (event) => {
        setState(event.target.files[0])
    }

    const fileUploadHandler = () => {
        const fd = new FormData()
        fd.append('file', state, state.name)
        axios.put(`http://localhost:8080/api/v1/songs/audio/update/${props.editedSong.id}`,
            fd
        ).then(
            res => {
                console.log(res.data)
                props.updatePage()
            }
        )
    }

    const songOnFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    const songOnFinish = (values) => {
        if (state !== undefined)
            fileUploadHandler()

        axios.put("http://localhost:8080/api/v1/songs/song/", {
            "title":values.name,
            "id":props.editedSong.id
        }).then(r=>{
            props.updatePage()
            }
        )
    };

    const songHandleOk = () => {
        props.setIsEditModalVisible(false);
    };

    const songHandleCancel = () => {
        props.setIsEditModalVisible(false);
    };

    return <Modal title="Edit Song" visible={props.isEditModalVisible} onOk={songHandleOk} onCancel={songHandleCancel}>
        <Form
            name="basic"
            labelCol={{
                span: 8,
            }}
            wrapperCol={{
                span: 16,
            }}
            onFinish={songOnFinish}
            onFinishFailed={songOnFinishFailed}
        >
            <Form.Item
                label="Name"
                name="name"
                rules={[
                    {
                        message: 'Update Name',
                    },
                ]}
            >
                <Input defaultValue={props.editedSong.title}/>
            </Form.Item>

            <Form.Item
                label="Upload file"
                name="update"
                rules={[
                    {
                        message: 'Upload file',
                    },
                ]}
            >
                <Input type={"file"} onChange={fileSelectedHandler}/>
            </Form.Item>

            <Form.Item
                wrapperCol={{
                    offset: 8,
                    span: 16,
                }}
            >
                <Space size={170}>
                    <Button icon={<CloseOutlined />} type="primary"onClick={deleteSong}>
                        Delete
                    </Button>
                    <Button type="primary" htmlType="submit">
                        Save
                    </Button>
                </Space>
            </Form.Item>

        </Form>
    </Modal>
}

export default SongEditModal