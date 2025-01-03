import { setOutput } from "npm:@actions/core"
import { TypedRegEx } from 'npm:typed-regex'

const jarRegex = TypedRegEx('nobedrock-(?<version>(\\d\\.?){2,})\\.jar')
const buildlibspath = new URL('../../build/libs', import.meta.url).pathname

for await (const dirEntry of Deno.readDir(buildlibspath)) {
    if(!dirEntry.isFile) continue;
    const match = jarRegex.match(dirEntry.name)
    if(!match.matched) continue;
    setOutput('version', match.groups?.version)
    setOutput('filename', dirEntry.name)
}