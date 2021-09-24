import {Button, Space, Tooltip} from "antd";
import {CaretRightOutlined, PauseOutlined, StepBackwardOutlined, StepForwardOutlined} from "@ant-design/icons";
import React, {useEffect, useState} from "react";

function PlayerControls(props) {

    const[playDisable, setPlayDisable] = useState(false)

    const getIcon = () => {
        if (!props.isPlaying || props.uuid === null)
            return <CaretRightOutlined />
        return <PauseOutlined />
    }

    useEffect(() => {
        if (props.uuid === null) {
            setPlayDisable(true)
        } else {
            setPlayDisable(false)
        }
    }, [props.uuid])

    return (
        <div>
            <Space>
                <Tooltip title="Play">
                    <Button type="primary" size="small" shape="circle" icon={<StepBackwardOutlined />}
                            onClick={() => {
                                props.SkipSong(false)
                                props.setSeconds(-1)
                            }}/>
                </Tooltip>
                <Tooltip title="Play">
                    <Button disabled={playDisable} type="primary" size="large" shape="circle" icon={getIcon()}
                            onClick={() => {
                                props.setIsPlaying(!props.isPlaying)
                            }}/>
                </Tooltip>
                <Tooltip title="Play">
                    <Button type="primary" size="small" shape="circle" icon={<StepForwardOutlined />}
                            onClick={() => {
                                props.SkipSong()
                                props.setSeconds(-1)
                            }}/>
                </Tooltip>
            </Space>
        </div>
    )
}

export default PlayerControls