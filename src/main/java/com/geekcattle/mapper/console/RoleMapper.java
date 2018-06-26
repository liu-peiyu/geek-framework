/*
 * Copyright (c) 2017-2018.  放牛极客<l_iupeiyu@qq.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * </p>
 *
 */

package com.geekcattle.mapper.console;

import com.geekcattle.model.console.Role;
import com.geekcattle.core.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * author geekcattle
 * date 2016/10/21 0021 下午 15:32
 */
@Service
public interface RoleMapper extends CustomerMapper<Role> {
    Set<String> findRoleByUserId(String userId);
    List<Role> selectRoleListByAdminId(String Id);
}
