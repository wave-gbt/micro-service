ngx.req.read_body()

if 
	ngx.var.arg_queryType ~= "province" and ngx.var.arg_queryType ~= "city" and
	ngx.var.arg_queryType ~= "district" 
then 
	ngx.say('[{"status"："0001","message":"Request parameter queryType illegal"}]')
	ngx.log(ngx.ERR,0001)
	ngx.exit(200)
end

local key = 'QUERYADDRESS' .. ngx.var.arg_queryType 

if ngx.var.arg_queryType ~= "province" 
then
	if ngx.var.arg_queryCode == nil or ngx.var.arg_queryCode == ""
	then 
		ngx.say('[{"status"："0002","message":"Request parameter queryCode necessary"}]')
		ngx.log(ngx.ERR,0002)
		ngx.exit(200)
	else 
		key = 'QUERYADDRESS' .. ngx.var.arg_queryType .. '_' .. ngx.var.arg_queryCode
	end
end

local redis = require "resty.redis"
local red = redis:new()
red:set_timeout(3000)
local ok, err = red:connect('10.142.195.66', 6379)
if not ok then
	ngx.status = 200
	ngx.say('[{"status":"0003","message":"connect redis error"}]')
	ngx.log(ngx.ERR,0003)
	ngx.exit(200)
end


local retMsg = red:get(key)

if retMsg == ngx.null or ngx.var.arg_refresh == 1
then
	local ret = ngx.location.capture(ngx.var.realuri,
	{args = { queryType = ngx.var.arg_queryType, queryCode = ngx.var.arg_queryCode}})
	if ret.status ~= 200
	then
		ngx.say('[{"status":"0004","message":"method request timeout"}]')
		ngx.log(ngx.ERR, 0004)
		ngx.exit(200)
	else
		local validTime = 60
		if ngx.var.arg_queryType == "province"  then
			validTime = 100
		end
		red:setex(key,validTime,ret.body)
		ngx.say('[{"status":"0000","message":' .. ret.body .. '}]')
	end
else
	ngx.say('[{"status":"0000","message":' .. retMsg .. '}]')
end