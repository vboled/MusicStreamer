import {Image} from "antd";
import React from 'react'

function getCover(uuid, w, defaultCoverName) {
    let name = uuid
    if (name === null)
        name = defaultCoverName
    return <Image
        width={w}
        preview={false}
        src={window.location.origin + '/img/' + name}
    />
}

export default getCover